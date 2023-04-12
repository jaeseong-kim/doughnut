package com.bread.web;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ProfileControllerTest {

  @Test
  public void searchRealProfile() {
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
  public void serachNoRealProfile() {
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
  public void serachDefault() {
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