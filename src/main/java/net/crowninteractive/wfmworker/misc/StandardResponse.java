/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import java.util.List;
import net.crowninteractive.wfmworker.service.Awesome;

/**
 *
 * @author osita
 */
public class StandardResponse {

    private static final int OK = 0;
    private static final int VALIDATION_ERROR = 200;
    private static final int NO_RECORDS_AVAILABLE = 400;

    private static final int CITY_NOT_FOUND = 700;
    private static final int RECORD_NOT_SAVED = 800;
    private static final int SYSTEM_ERROR = 900;
    private static final int INVALID_USER = 1000;
    private static final int UNKNOWN_USER = 1010;
    private static final int INVALID_USERNAME_OR_PASSWORD = 1020;
    private static final int INVALID_CREDENTIALS = 1030;
    private static final int MIN_LENGTH_SIX = 1040;
    private static final int CONFIRM_PASSWORD = 1050;
    private static final int INCORRECT_PASSWORD = 1060;
    private static final int INVALID_REQUEST = 1070;
    private static final int ADMIN_EMAIL_NOT_FOUND = 1100;
    private static final int INVALID_AMOUNT = 1200;
    private static final int PURSE_NOT_DEBITED = 1210;
    private static final int INSUFFICIENT_FUND = 1220;
    private static final int UNVERIFIED_WALLET_TRANSACTION = 1230;

    private static final int UNABLE_TO_COMPLETE = 1400;
    private static final int ERROR_DURING_PROCESSING = 1410;
    private static final int DISCONNECTION_QUEUE_TYPE_NOT_SET = 1500;
    private static final int RECONNECTION_QUEUE_TYPE_NOT_SET = 1600;
    private static final int INFLIGHT_QUEUE_TYPE_NOT_SET = 1700;
    private static final int INTEGRATION_CONNECTION_FAILED = 1800;

    public static Awesome validationErrors(List<String> errors) {
        return new Awesome(VALIDATION_ERROR, "Validation Errors", errors);
    }

    public static Awesome ok(Object obj) {
        if (obj != null) {
            return new Awesome(OK, "Successful", obj);
        } else {
            return new Awesome(OK, "Successful");
        }
    }

    public static Awesome ok() {
        return ok(null);
    }

    public static Awesome systemError() {
        return new Awesome(SYSTEM_ERROR, "System Error");
    }

    public static Awesome cityNotFound() {
        return new Awesome(CITY_NOT_FOUND, "City Not Found");
    }

    public static Awesome invalidUser() {
        return new Awesome(INVALID_USER, "Invalid User");
    }

    public static Awesome adminEmailNotFound() {
        return new Awesome(ADMIN_EMAIL_NOT_FOUND, "Admin User not found");
    }

    public static Awesome invalidUserOrPass() {
        return new Awesome(INVALID_USERNAME_OR_PASSWORD, "Invalid username or password");
    }

    public static Awesome noRecords() {
        return new Awesome(NO_RECORDS_AVAILABLE, "No records available");
    }

    public static Awesome validationErrors(String errors) {
        return new Awesome(VALIDATION_ERROR, "Validation Errors", errors);
    }

    public static Awesome validationError() {
        return new Awesome(VALIDATION_ERROR, "Validation Errors");
    }

    public static Awesome invalidCredentials() {
        return new Awesome(INVALID_CREDENTIALS, "Invalid Credentials");
    }

    public static Awesome unableToComplete() {
        return new Awesome(UNABLE_TO_COMPLETE, "Unable to complete");
    }

    public static Awesome minLengthSix() {
        return new Awesome(MIN_LENGTH_SIX, "Password should be at least 6 characters");
    }

    public static Awesome confirmPassword() {
        return new Awesome(CONFIRM_PASSWORD, "Comfirm password");
    }

    public static Awesome errorDuringProcessing() {
        return new Awesome(ERROR_DURING_PROCESSING, "Error during processing");
    }

    public static Awesome invalidAmount() {
        return new Awesome(INVALID_AMOUNT, "Invalid amount");
    }

    public static Awesome unknownUser() {
        return new Awesome(UNKNOWN_USER, "Unknown user");
    }

    public static Awesome purseNotDebited() {
        return new Awesome(PURSE_NOT_DEBITED, "Purse not debited");
    }

    public static Awesome insufficientFund() {
        return new Awesome(INSUFFICIENT_FUND, "Insufficient fund");
    }

    public static Awesome unverifiedWalletTransaction() {
        return new Awesome(UNVERIFIED_WALLET_TRANSACTION, "Unverified wallet transaction");
    }

    public static Awesome passwordIncorrect() {
        return new Awesome(INCORRECT_PASSWORD, "Incorrect password");
    }

    public static Awesome invalidRequest() {
        return new Awesome(INVALID_REQUEST, "Invalid request");
    }

    public static Awesome disconnectionQueueTypeNotSet() {
        return new Awesome(DISCONNECTION_QUEUE_TYPE_NOT_SET, "disconnection queue type not yet configured for emcc");
    }

    public static Awesome reconnectionQueueTypeNotSet() {
        return new Awesome(RECONNECTION_QUEUE_TYPE_NOT_SET, "reconnection queue type not yet configured for emcc");
    }

    public static Awesome inflightQueueTypeNotSet() {
        return new Awesome(INFLIGHT_QUEUE_TYPE_NOT_SET, "inflight queue type not yet configured for emcc");
    }

    public static Awesome cannotConnectToRemoteHost() {
        return new Awesome(INTEGRATION_CONNECTION_FAILED, "unable to connect to remote host");
    }
}
