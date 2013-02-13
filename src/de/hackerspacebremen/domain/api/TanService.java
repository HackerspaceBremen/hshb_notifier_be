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
package de.hackerspacebremen.domain.api;

import de.hackerspacebremen.data.entities.Tan;
import de.liedtke.business.api.BasicService;
import de.liedtke.validation.ValidationException;

/**
 * @author Steve Liedtke
 */
public interface TanService extends BasicService {

	Tan createNewTan();
	
	boolean checkTan(final String hash, final String name, final String decryptedPass, final String message)
		throws ValidationException;
}
