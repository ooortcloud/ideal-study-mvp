package com.idealstudy.mvp.security.service;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.security.userDetailsImpl.MemberDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberDto member = memberRepository.findByEmail(username);

        /*
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));

        return new User(username, member.getPassword(), authorities);
         */

        return new MemberDetails(member);
    }
}
