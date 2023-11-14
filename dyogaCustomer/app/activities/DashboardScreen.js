import {StatusBar} from 'expo-status-bar';
import {FlatList, Image, Pressable, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStoreKeys, constStrings} from "../utils/Constants";
import {DYogaColors} from "../utils/DYogaColors";
import EditText from "../components/EditText";
import {useEffect, useState} from "react";
import ClassInstanceItem from "../components/ClassInstanceItem";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import {auth} from "../firebase";
import {getAllClasses, queryClasses} from '../data/classRepository';
import moment from 'moment/moment';
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";

export default function DashboardScreen({navigation}) {

    const currentUser = auth.currentUser
    const [filterApplied, setFilterApplied] = useState(false)
    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([])
    const [tempClassInstances, setTempClassInstances] = useState([])
    const [searchValue, setSearchValue] = useState('');
    const [classInstancesInCart, setClassInstancesInCart] = useState({})

    const applyBookedFilter = () => {

    }

    const onSearch = (text) => {
        setSearchValue(text);

        if (!text) setTempClassInstances(classInstances);

        setTempClassInstances(classInstances.filter(ci => {

            return new moment(ci.course.time).format('h:mm a').toLowerCase().includes(text.toLowerCase())
                || ci.course.dayOfWeek.toLowerCase().includes(text.toLowerCase())
                || ci.classInstance.room.toLowerCase().includes(text.toLowerCase())
                || ci.course.type.toLowerCase().includes(text.toLowerCase())
                || new moment(ci.classInstance.date).format('Do MMMM YYYY').toLowerCase().includes(text.toLowerCase())

        }))
    }

    useEffect(() => {
        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            setClassInstancesInCart(classInstanceArray)
            console.log(classInstanceArray)
        })

        getAllClasses((result) => {
            setClassInstances(result);
            setTempClassInstances(result);
        })
    }, [])

    const addToCart = () => {
        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            classInstanceArray[selectedItem.docId] = selectedItem
            setClassInstancesInCart(classInstanceArray)
            AsyncStorage.setItem(constStoreKeys.bookedClasses, JSON.stringify(classInstanceArray)).then(() => {
                Toaster.show({type: 'success', message: "Class added to cart!"})
                setSelectedItem(null)
            }).catch(error => {
                console.log(error)
                Toaster.show({type: 'error', message: "Error adding class to cart"})
                setSelectedItem(null)
            })
        }).catch(error => {
            console.log(error)
            Toaster.show({type: 'error', message: "Error adding class to cart"})
            setSelectedItem(null)
        })
    }

    const isBooked = () => {
        return selectedItem && selectedItem.docId && classInstancesInCart &&
            Object.keys(classInstancesInCart ?? {}).includes(selectedItem.docId)
    }

    return (
        <View style={DashboardScreenStyles.container}>
            <View style={DashboardScreenStyles.header}>
                <Image style={{height: 70, width: 150}} source={require("../../assets/icon.png")}/>
                <Pressable onPress={() => navigation.navigate("CheckoutScreen")}>
                    <Image style={{height: 24, width: 24, marginHorizontal: constDimensions.screenPaddingHorizontal}}
                           source={require("../../assets/icons/cart.png")}/>
                </Pressable>
            </View>

            <View style={{paddingHorizontal: constDimensions.screenPaddingHorizontal, marginTop: 15}}>
                <EditText placeholder={constStrings.searchClassInstances} value={searchValue} onChangeText={onSearch}/>
            </View>

            <View style={{
                flexDirection: 'row',
                alignItems: 'center',
                paddingHorizontal: constDimensions.screenPaddingHorizontal,
                paddingVertical: 10
            }}>
                <Text>{constStrings.filter}:</Text>
                <Pressable style={{
                    ...DashboardScreenStyles.bookedButton,
                    backgroundColor: (filterApplied ? DYogaColors.bookedButtonBackgroundActive
                        : DYogaColors.bookedButtonBackground)
                }} onPress={applyBookedFilter}>
                    <Text style={{
                        fontFamily: 'RobotoSlab',
                        color: (filterApplied ? DYogaColors.white : DYogaColors.grey65)
                    }}>{constStrings.booked}</Text>
                </Pressable>
            </View>

            <View style={{paddingHorizontal: constDimensions.screenPaddingHorizontal}}>
                <FlatList data={tempClassInstances}
                          renderItem={({item}) => <ClassInstanceItem onPress={() => setSelectedItem(item)}
                                                                     item={item}/>}
                />
            </View>

            {selectedItem &&
                <ViewClassInstanceItem item={selectedItem} ctaPress={addToCart}
                                       isBooked={isBooked()}
                                       onToggle={() => setSelectedItem(null)}/>}

            <StatusBar style="auto"/>
        </View>
    );
}

const DashboardScreenStyles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: DYogaColors.screenBackground,
        paddingTop: constDimensions.screenPaddingTop
    },
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    bookedButton: {
        marginHorizontal: 5,
        fontFamily: "RobotoSlab",
        paddingVertical: 3,
        paddingHorizontal: 5,
        borderRadius: 5
    }
});

