import {Text, StyleSheet, View, Pressable, Image} from 'react-native';

const HeaderTitle = ({title, canGoBack, navigation}) => {
    return (<View style={{flexDirection: 'row', alignItems: 'center', marginBottom: 10}}>
        {canGoBack && <Pressable style={{marginRight: 10}} onPress={() => navigation.goBack()}>
            <Image
                style={{height: 20, width: 20}} resizeMode={"center"}
                source={require("../../assets/icons/back.png")}/>
        </Pressable>}
        <Text style={styles.baseText}>{title}</Text>
    </View>);
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