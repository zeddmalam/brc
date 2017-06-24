/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeddmalam.brc.dao.dataFile.exception;

/**
 *
 * @author zedd
 */
public class InvalidRouteDumpException extends Exception {

	/**
	 * Creates a new instance of <code>InvalidRouteDumpException</code> without
	 * detail message.
	 */
	public InvalidRouteDumpException() {
	}

	/**
	 * Constructs an instance of <code>InvalidRouteDumpException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InvalidRouteDumpException(String msg) {
		super(msg);
	}
}
