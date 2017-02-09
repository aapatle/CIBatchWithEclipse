package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	AccountRepository accountRepository;
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#depositAmount(int, int)
	 */
	@Override
	public int depositAmount(int accountNumber,int amount)throws InvalidAccountNumberException
	{
		Account account = accountRepository.searchAccount(accountNumber);
		if(account==null)
			throw new InvalidAccountNumberException();
		else
		{
			return amount;
		}
		/*if(accountNumber!=0)
		{
			account.setAmount(account.getAmount()+amount);
			
			return account.getAmount();
		}
		 
		else
			throw new InvalidAccountNumberException();*/
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#withdrawAmount(int, int)*/
	 
	@Override
	public int withdrawAmount(int accountNumber,int amount)throws InvalidAccountNumberException,InsufficientBalanceException
	{
		Account account = accountRepository.searchAccount(accountNumber);
		if(account!=null)
		{
			//account.setAmount(account.getAmount()-amount);
			//return account.getAmount();
			if(amount>account.getAmount())
			{
				throw new InsufficientBalanceException();
		    }
			else return amount;
		}
		else
			throw new InvalidAccountNumberException();
	}

	}
