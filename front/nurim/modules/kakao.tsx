import React, {useState} from 'react';
import {
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';
import {serverIP, apis} from '../common/urls';
import {useNavigation} from '@react-navigation/native';

import {RootStackParams, MainStackNavigationProp} from '../screens/RootStack';

export const signInWithKakao = async (navigation): Promise<void> => {
  try {
    const token = await login();
    const requestHeaders = new Headers();
    //requestHeaders.set('Content-Type', 'application/json');
    //requestHeaders.set('Authorization', JSON.stringify(token.accessToken));
    requestHeaders.set('Content-Type', 'application/json;charset=utf-8');
    fetch(serverIP + apis.kakaoLogin, {
      method: 'POST',
      headers: requestHeaders,
      body: JSON.stringify({
        access_token: JSON.stringify(token.accessToken),
      }),
    })
      .then(response => response.json())
      .then(response => {
        if (response.isFirst) navigation.navigate('SignUp');
        else return true;
      });
    getProfile();
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('login err', err);
  }
};

export const signOutWithKakao = async (): Promise<void> => {
  try {
    const message = await logout();

    setResult(message);
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('signOut error', err);
  }
};

export const getProfile = async (): Promise<void> => {
  try {
    const profile = await getKakaoProfile();

    console.log(JSON.stringify(profile));
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('signOut error', err);
  }
};

export const unlinkKakao = async (): Promise<void> => {
  try {
    const message = await unlink();

    setResult(message);
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('signOut error', err);
  }
};
