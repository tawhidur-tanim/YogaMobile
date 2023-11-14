import { Text, StyleSheet } from 'react-native';

const HeaderTitle = ({title}) => {
    return (<Text style={styles.baseText}>{title}</Text>);
}

const styles = StyleSheet.create({
    baseText: {
        fontFamily: "RobotoSlabBold",
        fontWeight: '500',
        fontSize: 32,
        color: '#000000'
    },
  });

export default HeaderTitle;