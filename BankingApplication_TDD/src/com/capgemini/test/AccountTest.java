package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	
	/*
	 * create account
	 * 1.when the amount is less than 500 system should throw exception
	 * 2.when the valid info is passed account should be created successfully
	 */

	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
    public void whenTheAmountIsLessThanFiveHundredSystemShouldThrowException() throws InsufficientInitialAmountException
    {
		accountService.createAccount(101, 400);
    }
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialAmountException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInvalidAccountNumberShouldThrowException() throws InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		accountService.depositAmount(0, 200);
	}
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenValidAccountNumberPassedAmountShouldBeDepositedSuccessfully() throws InvalidAccountNumberException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account.getAmount(),accountService.depositAmount(101, 5000));
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenInvalidAccountNumberShouldThrowExceptionWhenAmountIsWithdrawn() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		accountService.withdrawAmount(0, 600);
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenInsufficientBalanceThrowInsufficientBalanceException() throws InvalidAccountNumberException, InsufficientBalanceException
	{
		Account account =new Account();
		account.setAccountNumber(101);
		account.setAmount(500);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.withdrawAmount(101, 600);
	}
}
