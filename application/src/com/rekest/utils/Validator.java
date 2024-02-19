package com.rekest.utils;

import java.sql.Date;
import java.time.LocalDate;

public class Validator {

	private static final LocalDate ENSET_STARTS_AT = LocalDate.of(1988, 12, 12);

	/**
	 * Validate Text
	 * @param text To validate
	 * @return Boolean
	 */
	public static boolean validateText(String text){
		if(text.isEmpty() || text.isBlank()) return false;
		return true;
	}

	/**
	 * Validate Email Syntaxe
	 * @param email to Validate
	 * @return Boolean
	 */
	public static boolean validateEmail(String email){
		if(!validateText(email)) return false;
		
		if(!email.matches("^([a-zA-Z]+[a-zA-Z1-9\\\\_\\\\-]*)@gmail.com$")) return false;
		else return true;
	}

	/**
	 * Validate Phone Number
	 * @param phone to validate
	 * @return
	 */
	public static boolean validatePhoneNumber(String phone){
		if(!validateText(phone)) return false;

		if(!phone.matches("^0(6|7)\\d{8}$")) return false;
		else return true;
	}
	
	/**
	 * Validate Date
	 * @param ld Date to validate
	 * @return
	 */
	public static boolean validateDate(Date ld){
		if(ld == null) return false;
		if(ld.before(Date.valueOf(ENSET_STARTS_AT))) return false;
		return true;


	}

	public static boolean validateProfesseurTextData(String ...textData) {

		for(String txt : textData) {
			if(!validateText(txt)) return false;
		}
		return true;
	}

}
