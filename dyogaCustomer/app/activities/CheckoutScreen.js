import {StatusBar} from 'expo-status-bar';
import {Dimensions, FlatList, Image, RefreshControl, ScrollView, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStoreKeys, constStrings} from "../utils/Constants";
import HeaderTitle from "../components/HeaderTitle";
import {DYogaColors} from "../utils/DYogaColors";
import ClassInstanceItem from "../components/ClassInstanceItem";
import DYogaButton from "../components/DYogaButton";
import {useEffect, useState} from "react";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";
import {updateUserBookedClasses} from "../data/classRepository";
import {auth} from "../firebase";

export default function CheckoutScreen({navigation}) {

    const screenWidth = Dimensions.get('window').width;

    const currentUser = auth.currentUser
    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([])
    const [refreshing, setRefreshing] = useState(false)
    const [isCheckingOut, setIsCheckingOut] = useState(false)

    useEffect(() => {
        syncItems()
    }, [])

    const syncItems = () => {
        setRefreshing(true)
        AsyncStorage.getItem(constStoreKeys.classInstancesInCart).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            setClassInstances(Object.values(classInstanceArray))
        }).finally(() => setRefreshing(false))
    }
    const onClassInstancePressed = (item) => {
        if (isCheckingOut) return

        setSelectedItem(item)
    }

    const onClassInstanceDelete = (item) => {
        if (isCheckingOut) return

        AsyncStorage.getItem(constStoreKeys.classInstancesInCart).then(value => {
            try {
                const classInstanceArray = JSON.parse(value ?? "{}")
                delete classInstanceArray[item.docId]
                AsyncStorage.setItem(constStoreKeys.classInstancesInCart, JSON.stringify(classInstanceArray))
                    .then(() => setClassInstances(Object.values(classInstanceArray)))
            } catch (error) {
                console.error(error)
                Toaster.show({type: 'error', message: constStrings.errorDeletingItemFromCart})
            }
        })
    }

    const checkout = () => {
        if (isCheckingOut) return

        setIsCheckingOut(true)

        AsyncStorage.getItem(constStoreKeys.classInstancesInCart).then(value => {
            const classInstancesInCart = JSON.parse(value ?? "{}")
            updateUserBookedClasses(currentUser, classInstancesInCart).then(() => [
                AsyncStorage.removeItem(constStoreKeys.classInstancesInCart).then(() => {
                    setClassInstances([])
                    Toaster.show({type: 'success', message: constStrings.classBookedSuccessfully})
                    setIsCheckingOut(false)
                }).finally(() => setIsCheckingOut(false))
            ]).catch(error => {
                console.error(error)
                Toaster.show({type: 'success', message: constStrings.errorBookingClasses})
            }).finally(() => setIsCheckingOut(false))
        }).catch(error => {
            console.error(error)
            Toaster.show({type: 'success', message: constStrings.errorBookingClasses})
        }).finally(() => setIsCheckingOut(false))
    }

    return (
        <View style={CheckoutScreenStyles.container}>
            <View style={{...CheckoutScreenStyles.row, marginTop: constDimensions.screenPaddingTop}}>
                <HeaderTitle title={constStrings.yourCheckout} navigation={navigation} canGoBack={true}></HeaderTitle>
            </View>
            <Text style={CheckoutScreenStyles.label}>
                You are ready to book your classes. Proceed to checkout
            </Text>
            <View style={{marginTop: 10}}>
                <FlatList data={classInstances} renderItem={({item}) =>
                    <ClassInstanceItem item={item} canDelete={true} onDelete={onClassInstanceDelete}
                                       onPress={onClassInstancePressed}/>}
                          refreshControl={<RefreshControl refreshing={refreshing} onRefresh={syncItems}/>}
                />
            </View>

            {classInstances.length === 0 &&
                <ScrollView refreshControl={<RefreshControl refreshing={refreshing} onRefresh={syncItems}/>}>
                    <View style={{width: '100%', alignItems: 'center', marginTop: '50%'}}>
                        <Image resizeMode={"center"} style={{height: 50, width: 50}}
                               source={require("../../assets/icons/info.png")}/>
                        <Text style={{
                            marginTop: 10,
                            fontFamily: 'RobotoSlab',
                            color: DYogaColors.grey65
                        }}>{constStrings.noClassesAvailable}</Text>
                    </View>
                </ScrollView>}

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
