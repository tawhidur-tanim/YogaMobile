import React, {useState} from 'react';
import {View, TextInput, Button, Text, StyleSheet} from 'react-native';
import {DYogaColors} from "../utils/DYogaColors";

const EditText = ({
                      placeholder, secureTextEntry = false, value = '', onChangeText = text => {
    }, autoFocus = false, returnKeyType, innerRef, onSubmitEditing
                  }) => {
    return (<TextInput style={styles.inputStyle} placeholder={placeholder} value={value} autoFocus={autoFocus}
                       onChangeText={onChangeText} secureTextEntry={secureTextEntry} returnKeyType={returnKeyType}
                       onSubmitEditing={onSubmitEditing}
                       ref={innerRef} selectionColor={DYogaColors.colorSecondary}/>
    );
}

const styles = StyleSheet.create({
    inputStyle: {
        fontFamily: "RobotoSlab",
        width: '100%',
        height: 47,
        backgroundColor: DYogaColors.inputBackground,
        borderRadius: 6,
        padding: 15
    },
});

export default EditText;

