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
package de.hackerspacebremen.domain.val;

import de.hackerspacebremen.data.entities.GCMAuth;
import de.hackerspacebremen.domain.api.GCMAuthService;
import de.liedtke.business.validation.BasicServiceValidation;
import de.liedtke.validation.ValidationException;

public class GCMAuthServiceValidation extends BasicServiceValidation implements GCMAuthService{

	public GCMAuthServiceValidation(final GCMAuthService service){
		this.basicService = service;
	}

	@Override
	public GCMAuth getAuthToken() throws ValidationException{
		return ((GCMAuthService)basicService).getAuthToken();
	}

}
