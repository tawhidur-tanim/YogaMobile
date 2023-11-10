import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View, Text } from 'react-native';
import EditText from '../components/EditText';
import HeaderTitle from '../components/HeaderTitle';
import DYogaButton from '../components/DYogaButton';


export default function AuthScreen() {
    return (
    <View style={styles.container}>

     <View style = {{...styles.row, marginTop: 60}}>
         <HeaderTitle title={"Register | Login"}></HeaderTitle>
    </View>

    <View style = {styles.row}>
        <Text>
        New or have an account?{"\n"}Enter your email and a password
        </Text>
    </View>

    <View style = {styles.row}>
        <View style={styles.column}>

            <Text style={styles.label}>Email</Text>
            <EditText placeholder={"example@example.com"}></EditText>

        </View>
    </View>


    <View style = {styles.row}>
        
        <View style={styles.column}>
            <Text style={styles.label}>Password</Text>
            <EditText placeholder={"password"} secureTextEntry={true}></EditText>
        </View>


    </View>

    <View style = {{...styles.row, justifyContent:'center'}}>
        <DYogaButton title='Continue' onPress={() => console.log("pressed")}/>
    </View>
        <StatusBar style="auto" />

    </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        paddingLeft: 30,
        paddingRight: 30,
        width:'100%',
    },

    row: {
        flexDirection: 'row',
        justifyContent: 'flex-start',
        alignItems: 'center',
        margin: 10,

      },

    column:{
        flexDirection: 'column'
    },
    label:{
        paddingBottom: 10
    }
});


