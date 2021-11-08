package com.portfolio.complete.springboot.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProfileControllerUnitTest {

    @Test
    @DisplayName("real profile이 조회된다.")
    void search_real_profile() {
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then

        assertThat(profile).isEqualTo(expectedProfile);

    }

    @Test
    @DisplayName("real이 없으면 real1이 조회된다.")
    void search_real1_if_not_exist_real() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);

    }

    @Test
    @DisplayName("액티브 프로필이 없으면 default가 조회된다.")
    void not_active_profile_search_default() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertThat(profile).isEqualTo(expectedProfile);

    }
}