package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import com.delta.commerce.entity.Transaction;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.ProfileRepository;
import com.delta.commerce.repository.TransactionsRepository;
import com.delta.commerce.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Profile createProfile(ProfileRequestDto dto) {

        var isRegistered = this.profileRepository.findProfileByProfileName(dto.getProfileName());

        if (isRegistered.isPresent()) {
            throw new CustomException(ErrorCustom.PROFILE_ALREADY_EXISTS);
        }

        List<String> types = List.of("WRITE", "READ");
        Set<Transaction> transactions = new HashSet<>();

        types.forEach(data -> {
            transactions.add(
                    this.transactionsRepository.save(
                            new Transaction(dto.getProfileName().toUpperCase() + "_" + data)
                    ));
        });

        var profile = new Profile();
        profile.setProfileName(dto.getProfileName().toUpperCase());
        profile.setTransactions(transactions);

        return this.profileRepository.save(profile);
    }



}
