package com.nvm10.loans.command.controller;

import com.nvm10.loans.command.CreateLoanCommand;
import com.nvm10.loans.command.DeleteLoanCommand;
import com.nvm10.loans.command.UpdateLoanCommand;
import com.nvm10.loans.constants.LoansConstants;
import com.nvm10.loans.dto.LoansDto;
import com.nvm10.loans.dto.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
public class LoanCommandController {

    private final CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam("mobileNumber")
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        long randomLoanNumber = 1000000000L + new Random().nextInt(900000000);
        CreateLoanCommand createCommand = CreateLoanCommand.builder()
                .loanNumber(randomLoanNumber).mobileNumber(mobileNumber)
                .loanStatus("APPROVED")
                .loanType(LoansConstants.HOME_LOAN).totalLoan(LoansConstants.NEW_LOAN_LIMIT)
                .amountPaid(0).outstandingAmount(LoansConstants.NEW_LOAN_LIMIT)
                .activeSw(LoansConstants.ACTIVE_SW).build();
        commandGateway.send(createCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        UpdateLoanCommand command = UpdateLoanCommand.builder()
                .loanNumber(loansDto.getLoanNumber())
                .totalLoan(loansDto.getTotalLoan())
                .loanType(loansDto.getLoanType())
                .amountPaid(loansDto.getAmountPaid())
                .outstandingAmount(loansDto.getOutstandingAmount())
                .activeSw(loansDto.isActiveSw()).build();
        commandGateway.send(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));

    }

    @PatchMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam("loanNumber")
                                                         Long loanNumber) {
        DeleteLoanCommand command = DeleteLoanCommand.builder()
                .loanNumber(loanNumber).build();
        commandGateway.send(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));

    }
}
