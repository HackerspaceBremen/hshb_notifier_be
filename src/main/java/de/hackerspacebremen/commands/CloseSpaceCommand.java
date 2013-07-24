/*
 * Hackerspace Bremen Backend - An Open-Space-Notifier
 * 
 * Copyright (C) 2012 Steve Liedtke <sliedtke57@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation; either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU General Public License for more details.
 * 
 * You can find a copy of the GNU General Public License on http://www.gnu.org/licenses/gpl.html.
 * 
 * Contributors:
 *     Steve Liedtke <sliedtke57@gmail.com>
 */
package de.hackerspacebremen.commands;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.inject.Inject;

import de.hackerspacebremen.common.AppConstants;
import de.hackerspacebremen.data.entities.DoorKeyKeeper;
import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.data.entities.SpaceStatus;
import de.hackerspacebremen.deprecated.presentation.WebCommand;
import de.hackerspacebremen.deprecated.util.Encryption;
import de.hackerspacebremen.deprecated.validation.ValidationException;
import de.hackerspacebremen.domain.api.DoorKeyKeeperService;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.SpaceStatusService;

public class CloseSpaceCommand extends WebCommand{

	@Inject
	private DoorKeyKeeperService keeperService;
	
	@Inject
	private SpaceStatusService statusService;
	
	@Inject
	private GCMAuthService gcmAuthService;
	
	@Inject
	private GCMDataService gcmDataService;
	
	@Override
	public void process() throws ServletException, IOException {
		this.registerService(statusService, keeperService, gcmAuthService, gcmDataService);
		try{
			final DoorKeyKeeper keeper = keeperService.findKeyKeeper(this.req.getParameter("name"), this.req.getParameter("pass"));
			if(keeper==null){
				this.handleError(1);
			}else{
				SpaceStatus status = statusService.currentStatus();
				if(status == null || status.getStatus().equals(AppConstants.OPEN)){
					status = statusService.closeSpace(keeper, this.req.getParameter("message"));
					final GCMAuth authToken = gcmAuthService.getAuthToken();
					if(authToken!=null){
						final Queue queue = QueueFactory.getDefaultQueue();
						TaskOptions taskOpt = TaskOptions.Builder.withUrl("/cmd/gcm");
						taskOpt.method(Method.POST);
						taskOpt.taskName("task_cd2m_close_" + new Date().getTime());
						taskOpt.param("token", Encryption.encryptSHA256(authToken.getToken()+KeyFactory.keyToString(status.getKey())));
						queue.add(taskOpt);
					}
					this.handleSuccess("Space was closed", null);
				}else{
					this.handleError(4);
				}
			}
		}catch(ValidationException ve){
			this.handleError(ve);
		}
		
		super.process();
	}
}
