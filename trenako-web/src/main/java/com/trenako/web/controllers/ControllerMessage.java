/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.controllers;

/**
 * It represents a message returned by a controller.
 * @author Carlo Micieli
 *
 */
public class ControllerMessage {

	/**
	 * The controller message type {@code INFO}.
	 */
	public final static String INFO = "info";
	
	/**
	 * The controller message type {@code SUCCESS}.
	 */
	public final static String SUCCESS = "success";
	
	/**
	 * The controller message type {@code ERROR}.
	 */
	public final static String ERROR = "error";
	
	private final String type;
	private final String message;
	private final Object[] args;
	
	private ControllerMessage(String type, String message, Object[] args) {
		this.type = type;
		this.message = message;
		this.args = args;
	}
	
	/**
	 * Creates a new {@code info} controller message.
	 * @param message the message code
	 * @param args the message arguments
	 * @return a {@code ControllerMessage}
	 */
	public static ControllerMessage info(String message, Object... args) {
		return new ControllerMessage(INFO, message, args);
	}
	
	/**
	 * Creates a new {@code error} controller message.
	 * @param message the message code
	 * @param args the message arguments
	 * @return a {@code ControllerMessage}
	 */
	public static ControllerMessage error(String message, Object... args) {
		return new ControllerMessage(ERROR, message, args);
	}
	
	/**
	 * Creates a new {@code success} controller message.
	 * @param message the message code
	 * @param args the message arguments
	 * @return a {@code ControllerMessage}
	 */
	public static ControllerMessage success(String message, Object... args) {
		return new ControllerMessage(SUCCESS, message, args);
	}

	/**
	 * Returns the {@code ControllerMessage} message type.
	 * @return the message type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the {@code ControllerMessage} message code.
	 * @return the message code
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the {@code ControllerMessage} message arguments.
	 * @return the message arguments
	 */
	public Object[] getArgs() {
		return args;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof ControllerMessage)) return false;
		
		ControllerMessage other = (ControllerMessage) obj;
		return this.type.equals(other.type) &&
				this.message.equals(other.message) &&
				this.args.equals(other.args);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("message{type: ")
			.append(type)
			.append(", message: ")
			.append(message)
			.append(", args: [")
			.append(args)
			.append("]")
			.toString();
	}
}
