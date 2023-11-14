import {StatusBar} from 'expo-status-bar';
import {Dimensions, FlatList, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStrings} from "../utils/Constants";
import HeaderTitle from "../components/HeaderTitle";
import {DYogaColors} from "../utils/DYogaColors";
import ClassInstanceItem from "../components/ClassInstanceItem";
import DYogaButton from "../components/DYogaButton";
import {useState} from "react";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";

export default function CheckoutScreen() {

    const screenWidth = Dimensions.get('window').width;

    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([
        {key: 'Devin'},
        {key: '2'},
    ])

    const onClassInstancePressed = () => {

    }

    const onClassInstanceDelete = () => {

    }

    return (
        <View style={CheckoutScreenStyles.container}>
            <View style={{...CheckoutScreenStyles.row, marginTop: constDimensions.screenPaddingTop}}>
                <HeaderTitle title={constStrings.yourCheckout}></HeaderTitle>
            </View>
            <Text style={CheckoutScreenStyles.label}>
                You are ready to book your classes. Proceed to checkout
            </Text>
            <View style={{marginTop: 10}}>
                <FlatList data={classInstances} renderItem={({item}) => <ClassInstanceItem item={item} canDelete={true}
                                                                                           onDelete={onClassInstanceDelete}
                                                                                           onPress={onClassInstancePressed}/>}
                />
            </View>

            {selectedItem && <ViewClassInstanceItem item={selectedItem} />}

            <View style={{...CheckoutScreenStyles.checkoutButtonContainer, width: screenWidth}}>
                <DYogaButton title={`${constStrings.checkout} (${classInstances.length})`}
                             buttonTextStyles={{fontWeight: '500', color: DYogaColors.white}}/>
            </View>
            <StatusBar style="auto"/>
        </View>
    );
}

const CheckoutScreenStyles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: DYogaColors.screenBackground,
        paddingLeft: constDimensions.screenPaddingHorizontal,
        paddingRight: constDimensions.screenPaddingHorizontal,
        width: '100%',
    },
    row: {
        flexDirection: 'row',
        marginBottom: 2,
    },
    label: {
        fontFamily: "RobotoSlab",
        color: DYogaColors.grey,
        fontSize: 12,
        paddingBottom: 7
    },
    checkoutButtonContainer: {
        alignItems: 'center',
        position: 'absolute',
        bottom: 40
    }
});


