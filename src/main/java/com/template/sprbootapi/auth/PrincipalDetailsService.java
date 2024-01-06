package com.template.sprbootapi.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.template.sprbootapi.data.entity.Tbuser;
import com.template.sprbootapi.data.repository.TbuserRepository;
import com.template.sprbootapi.exception.NoMatchingUserException;

@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	private final TbuserRepository tbuserRepository;
	
	public PrincipalDetailsService(TbuserRepository tbuserRepository) {
		this.tbuserRepository = tbuserRepository;
	}
	
    /**
	 *  principalDetails 생성을 위한 함수.
	 *  email로 tbuser 조회, principalDetails 생성
	 *  
	 *  @param String email
	 *  @return (UserDetails)PrincipalDetails
	 *  @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Tbuser tbuser = tbuserRepository.findByUsername(username);
		if(tbuser == null) {
			throw new NoMatchingUserException("username : " + username);
		}
		return new PrincipalDetails(tbuser);
	}
	
}