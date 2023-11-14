import {StatusBar} from 'expo-status-bar';
import {Dimensions, FlatList, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStoreKeys, constStrings} from "../utils/Constants";
import HeaderTitle from "../components/HeaderTitle";
import {DYogaColors} from "../utils/DYogaColors";
import ClassInstanceItem from "../components/ClassInstanceItem";
import DYogaButton from "../components/DYogaButton";
import {useEffect, useState} from "react";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";
import {auth} from "../firebase";
import {updateUserBookedClasses} from "../data/classRepository";

export default function CheckoutScreen() {

    const screenWidth = Dimensions.get('window').width;

    const currentUser = auth.currentUser
    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([])
    const [isCheckingOut, setIsCheckingOut] = useState(false)

    useEffect(() => {
        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            setClassInstances(Object.values(classInstanceArray))
        })
    }, [classInstances])

    const onClassInstancePressed = (item) => {
        if (isCheckingOut) return

        setSelectedItem(item)
    }

    const onClassInstanceDelete = (item) => {
        if (isCheckingOut) return

        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            try {
                const classInstanceArray = JSON.parse(value ?? "{}")
                delete classInstanceArray[item.docId]
                AsyncStorage.setItem(constStoreKeys.bookedClasses, JSON.stringify(classInstanceArray))
                    .then(() => setClassInstances(Object.values(classInstanceArray)))
            } catch (error) {
                console.log(error)
                Toaster.show({type: 'error', message: "Error deleting item from cart. Try again!"})
            }
        })
    }

    const checkout = () => {
        if (isCheckingOut) return

        setIsCheckingOut(true)

        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstancesInCart = JSON.parse(value ?? "{}")
            updateUserBookedClasses(classInstancesInCart).then(() => [
                AsyncStorage.removeItem(constStoreKeys.bookedClasses).then(() => {
                    setIsCheckingOut(false)
                    setClassInstances([])
                    Toaster.show({type: 'success', message: "Classes booked successfully! 🎉"})
                }).finally(() => setIsCheckingOut(false))
            ]).catch(error => {
                console.log(error)
                Toaster.show({type: 'success', message: "Error booking classes!"})
            }).finally(() => setIsCheckingOut(false))
        }).catch(error => {
            console.log(error)
            Toaster.show({type: 'success', message: "Error booking classes!"})
        }).finally(() => setIsCheckingOut(false))
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
                <FlatList data={classInstances} renderItem={({item}) =>
                    <ClassInstanceItem item={item} canDelete={true} onDelete={onClassInstanceDelete}
                                       onPress={onClassInstancePressed}/>}
                />
            </View>

            {selectedItem && <ViewClassInstanceItem item={selectedItem}/>}

            {classInstances.length > 0 &&
                <View style={{...CheckoutScreenStyles.checkoutButtonContainer, width: screenWidth}}>
                    <DYogaButton title={`${constStrings.checkout} (${classInstances.length})`} onPress={checkout}
                                 isLoading={isCheckingOut}
                                 buttonTextStyles={{fontWeight: '500', color: DYogaColors.white}}/>
                </View>}
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


