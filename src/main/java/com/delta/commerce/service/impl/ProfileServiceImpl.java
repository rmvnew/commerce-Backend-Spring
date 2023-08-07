package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.ProfileRequestDto;
import com.delta.commerce.entity.Profile;
import com.delta.commerce.entity.Transaction;
import com.delta.commerce.enums.HistoricDescriptionEnum;
import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import com.delta.commerce.repository.ProfileRepository;
import com.delta.commerce.repository.TransactionsRepository;
import com.delta.commerce.service.HistoricService;
import com.delta.commerce.service.ProfileService;
import com.delta.commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoricService historicService;

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
        profile.setCreateAt(LocalDateTime.now());
        profile.setUpdateAt(LocalDateTime.now());
        profile.setActive(true);

        var profileSaved = this.profileRepository.save(profile);

        this.historicService.saveHistoric(
                Profile.class, profileSaved.getProfileId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.PROFILE_CREATE
        );

        return profileSaved;
    }


    @Override
    public Page<Profile> getAllProfiles(String name, Pageable page) {
        return this.profileRepository.getAllProfiles(name, page);
    }

    @Override
    public Profile findById(Long id) {
        return this.profileRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCustom.NOT_FOUND));
    }

    @Override
    public Profile updateProfile(ProfileRequestDto dto, Long id) {

        var profile = this.findById(id);
        profile.setProfileName(dto.getProfileName());
        profile.setUpdateAt(LocalDateTime.now());
        var profileSaved = this.profileRepository.save(profile);

        this.historicService.saveHistoric(
                Profile.class, profileSaved.getProfileId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.PROFILE_UPDATE
        );

        return profileSaved;
    }

    @Override
    public void changeStatus(Long id) {

        var profile = this.findById(id);
        profile.setActive(false);
        var profileSaved = this.profileRepository.save(profile);

        this.historicService.saveHistoric(
                Profile.class, profileSaved.getProfileId(), this.userService.getLoggedInUser(),
                HistoricDescriptionEnum.PROFILE_DESACTIVED
        );

    }

    @Override
    public List<Profile> getListProfiles() {
        return this.profileRepository.findAll();
    }


}
