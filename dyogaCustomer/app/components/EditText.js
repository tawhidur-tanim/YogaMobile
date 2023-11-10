import React, { useState } from 'react';
import { View, TextInput, Button, Text, StyleSheet } from 'react-native';

const EditText = ({placeholder, secureTextEntry = false}) => {
    return (

      <TextInput
      style={styles.inputStyle}
      placeholder={placeholder}
      secureTextEntry={secureTextEntry}
      />

    );
}

const styles = StyleSheet.create({
  inputStyle: {
    width: 314, 
    height: 47,
    backgroundColor: 'rgba(240, 240, 240, 0.38)', 
    borderRadius: 6,
    padding: 10
   
  },
});


export default EditText;

