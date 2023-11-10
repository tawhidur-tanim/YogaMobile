import { Text, StyleSheet } from 'react-native';


const HeaderTitle = ({title}) => {
    return (
    <Text style={styles.baseText}>
       {title}
      </Text>
    );
}



const styles = StyleSheet.create({
    baseText: {
        fontSize: 32, 
        fontWeight: '500', 
        color: '#000000', 
        textAlign: 'center', 
    },
   
  });

export default HeaderTitle;