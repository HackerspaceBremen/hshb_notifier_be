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
package de.hackerspacebremen.domain;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Result;
import com.google.inject.Inject;
import com.google.inject.Provider;

import de.hackerspacebremen.data.api.GCMDataDAO;
import de.hackerspacebremen.data.entities.GCMData;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.val.ValidationException;
import de.hackerspacebremen.gcm.GCMMessageSender;

public class GCMDataServiceImpl implements GCMDataService{
	
	@Inject
	private GCMDataDAO gcmDataDAO;
	
	@Inject
	private Provider<GCMMessageSender> gcmMessageSender;
	
	/**
     * static attribute used for logging.
     */
    private static final Logger logger = Logger.getLogger(GCMDataServiceImpl.class.getName());
	
	@Override
	public void register(final String deviceId, final String registrationId) {
		GCMData gcmData = gcmDataDAO.findByDeviceId(deviceId);
		if(gcmData == null){
			gcmData = new GCMData();
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			gcmDataDAO.persist(gcmData);
		}else if(!gcmData.getRegistrationId().equals(registrationId)){
			gcmData.setDeviceId(deviceId);
			gcmData.setRegistrationId(registrationId);
			gcmDataDAO.persist(gcmData);
		}
	}
	
	@Override
	public void unregister(final String deviceId)
			throws ValidationException {
		GCMData gcmData = gcmDataDAO.findByDeviceId(deviceId);
		gcmDataDAO.delete(gcmData);
	}

	@Override
	public void sendMessageToDevices(final String message) throws ValidationException{
		final List<GCMData> devices = gcmDataDAO.findAll();
		for(final GCMData gcmData : devices){
			final GCMMessageSender sender = gcmMessageSender.get();
			Result result;
			try {
				result = sender.sendMessage(message, gcmData.getRegistrationId());
			
				if (result.getMessageId() != null) {
					String canonicalRegId = result.getCanonicalRegistrationId();
					if (canonicalRegId != null) {
						// same device has more than on registration ID: update database
						gcmData.setRegistrationId(canonicalRegId);
						gcmDataDAO.persist(gcmData);
					}
				} else {
					String error = result.getErrorCodeName();
					if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
						// application has been removed from device - unregister database
						gcmDataDAO.delete(gcmData);
					}
				}
			} catch (IOException e) {
				logger.severe("Unexpected IOException occured: " + e.getMessage());
			}
		}
	}
}
