package com.nvm10.loans.service;

import com.nvm10.common.dto.MobileNumberUpdateDto;
import com.nvm10.loans.dto.LoansDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     *
     * @param loanNumber - Input Loan Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    boolean deleteLoan(Long loanNumber);

    /**
     *
     * @param mobileNumberUpdateDto - MobileNumberUpdateDto Object
     * @return boolean indicating if the update of Mobile Number is successful or not
     */
    boolean updateMobileNumber(MobileNumberUpdateDto mobileNumberUpdateDto);
}
