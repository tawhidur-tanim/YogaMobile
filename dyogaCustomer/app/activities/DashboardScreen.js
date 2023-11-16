import {StatusBar} from 'expo-status-bar';
import {FlatList, Image, Pressable, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStoreKeys, constStrings} from "../utils/Constants";
import {DYogaColors} from "../utils/DYogaColors";
import EditText from "../components/EditText";
import {useEffect, useState} from "react";
import ClassInstanceItem from "../components/ClassInstanceItem";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import {auth} from "../firebase";
import {getAllBookedClasses, getAllClasses} from '../data/classRepository';
import moment from 'moment/moment';
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";
import {useFocusEffect} from "@react-navigation/native";
import { useIsFocused } from '@react-navigation/native';

export default function DashboardScreen({navigation}) {

    const currentUser = auth.currentUser
    const [filterApplied, setFilterApplied] = useState(false)
    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([])
    const [filterClassInstances, setFilterClassInstances] = useState([])
    const [searchValue, setSearchValue] = useState('');
    const [classInstancesInCart, setClassInstancesInCart] = useState({})
    const [bookedClasses, setBookedClasses] = useState({})

    const isFocused = useIsFocused()

    const applyBookedFilter = () => setFilterApplied(!filterApplied);

    const onSearch = (text) => {
        setSearchValue(text);

        if (!text) setFilterClassInstances(classInstances);

        setFilterClassInstances(classInstances.filter(ci => {

            return new moment(ci.course.time).format('h:mm a').toLowerCase().includes(text.toLowerCase())
                || ci.course.dayOfWeek.toLowerCase().includes(text.toLowerCase())
                || ci.classInstance.room.toLowerCase().includes(text.toLowerCase())
                || ci.course.type.toLowerCase().includes(text.toLowerCase())
                || new moment(ci.classInstance.date).format('Do MMMM YYYY').toLowerCase().includes(text.toLowerCase())

        }))
    }

    useEffect(() => {
        getAllClasses((result) => {
            setClassInstances(result);
            setFilterClassInstances(result);
        })

    
        syncCartItems()

    }, [isFocused])

    useEffect(() =>{
        console.log(bookedClasses);

        if(filterApplied){
            setFilterClassInstances(Object.values(bookedClasses));
        }
        else{
            setFilterClassInstances(classInstances);
        }

    },[filterApplied])

    function syncCartItems() {
        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            setClassInstancesInCart(classInstanceArray)
        })

        getAllBookedClasses((result) => {
            setBookedClasses(result)
            console.log("fired", result);
        })
    }

    // todo: this function is being called frequently, optimize it. Don't use
    // useFocusEffect(() => {
    //     if(useIsFocused) {
    //         syncCartItems()
    //     }
    // })

    const addToCart = () => {
        setSelectedItem(null)
        AsyncStorage.getItem(constStoreKeys.bookedClasses).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            classInstanceArray[selectedItem.docId] = selectedItem
            setClassInstancesInCart(classInstanceArray)
            AsyncStorage.setItem(constStoreKeys.bookedClasses, JSON.stringify(classInstanceArray)).then(() => {
                Toaster.show({type: 'success', message: "Class added to cart!"})
            }).catch(error => {
                console.log(error)
                Toaster.show({type: 'error', message: "Error adding class to cart"})
            })
        }).catch(error => {
            console.log(error)
            Toaster.show({type: 'error', message: "Error adding class to cart"})
        })
    }

    const isBooked = (item) => {
        return item && item.docId && classInstancesInCart &&
            Object.keys(classInstancesInCart ?? {}).includes(item.docId)
    }

    return (
        <View style={DashboardScreenStyles.container}>
            <View style={DashboardScreenStyles.header}>
                <Image style={{height: 70, width: 150}} source={require("../../assets/icon.png")}/>
                <Pressable onPress={() => navigation.navigate("CheckoutScreen")} style={{display: 'flex'}}>
                    
                    <View style={{ position: 'relative'}}>
                    {classInstancesInCart && Object.keys(classInstancesInCart).length > 0 &&
                        <Text style={DashboardScreenStyles.badge}>
                        
                            {Object.keys(classInstancesInCart).length}
                        </Text>}

                        <Image style={{height: 24, width: 24, marginHorizontal: constDimensions.screenPaddingHorizontal}}
                           source={require("../../assets/icons/cart.png")}/>
                    </View>
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
                <FlatList data={filterClassInstances}
                          renderItem={({item}) => <ClassInstanceItem onPress={() => setSelectedItem(item)}
                                                                     item={item}/>}
                />
            </View>

            {selectedItem &&
                <ViewClassInstanceItem item={selectedItem} ctaPress={addToCart}
                                       isBooked={isBooked(selectedItem)}
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
    },
    badge: {
        backgroundColor: DYogaColors.grey65,
        color: DYogaColors.white,
        verticalAlign: 'middle',
        width: 20,
        height: 20,
        borderRadius: 50,
        verticalAlign: 'middle',
        textAlign: 'center',
        position : 'absolute',
        right:10,
        bottom: 10,
        zIndex:2
    }
});

