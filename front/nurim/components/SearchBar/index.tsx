import {StyleSheet, View} from 'react-native';
import {TextInput} from 'react-native-gesture-handler';
import Icon from 'react-native-vector-icons/FontAwesome5';
import {SafeAreaView} from 'react-native-safe-area-context';
import React, {useState} from 'react';

const styles = StyleSheet.create({
  // 검색창 스타일
  header: {
    alignSelf: 'center',
    borderWidth: 1,
    borderRadius: 4,
    paddingHorizontal: 15,
    paddingVertical: 5,
    width: '100%',
    flexDirection: 'row',
    backgroundColor: 'white',
  },
  search: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  input: {
    paddingHorizontal: 3,
    width: '70%',
  },
});

type Props = {
  openDrawer: void;
  getInput: (searchText: string) => void;
};

const SearchBar = ({openDrawer, getInput}: Props) => {
  // 검색창
  const [text, onChangeText] = useState<string>('');
  return (
    <SafeAreaView style={styles.header}>
      <View style={styles.search}>
        <Icon
          name="bars"
          size={30}
          color="#000000"
          onPress={() => openDrawer()}
        />
        <TextInput
          placeholder="장소 검색"
          style={styles.input}
          onChangeText={onChangeText}
          value={text}
        />
        <Icon
          name="search"
          size={25}
          color="#000000"
          onPress={() => getInput(text)}
          onPressOut={() => onChangeText('')}
        />
      </View>
    </SafeAreaView>
  );
};

export default SearchBar;
