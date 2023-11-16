import {StatusBar} from 'expo-status-bar';
import {FlatList, Image, Pressable, RefreshControl, ScrollView, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStoreKeys, constStrings} from "../utils/Constants";
import {DYogaColors} from "../utils/DYogaColors";
import EditText from "../components/EditText";
import {useEffect, useState} from "react";
import ClassInstanceItem from "../components/ClassInstanceItem";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import {ACCOUNT_AUTHENTICATED, auth} from "../firebase";
import {getAllBookedClasses, getAllClasses} from '../data/classRepository';
import AsyncStorage from "@react-native-async-storage/async-storage";
import Toaster from "../components/Toaster";
import {useIsFocused} from '@react-navigation/native';
import {
    filterClassInstancesByDate,
    isInClassesInstanceArray,
    orderClassInstances,
    searchClassInstances
} from "../utils/ClassInstanceUtils";
import UseFilterView from "../components/UseFilterView";

export default function DashboardScreen({navigation}) {

    const isFocused = useIsFocused()
    const currentUser = auth.currentUser

    const [refreshing, setRefreshing] = useState(false)
    const [filterParams, setFilterParams] = useState()
    const [selectedItem, setSelectedItem] = useState()
    const [showFilterDialog, setShowFilterDialog] = useState(false)
    const [classInstances, setClassInstances] = useState([])
    const [filterClassInstances, setFilterClassInstances] = useState([])
    const [searchValue, setSearchValue] = useState('');
    const [classInstancesInCart, setClassInstancesInCart] = useState({})
    const [bookedClasses, setBookedClasses] = useState({})

    useEffect(() => {
        syncDashboardItems()
    }, [isFocused, currentUser])

    useEffect(() => {
        let filteredClassInstances = classInstances
        if (filterParams) {
            const {bookFiltered, filterTimeFrom, filterTimeTo} = filterParams
            if (bookFiltered) {
                filteredClassInstances = Object.values(bookedClasses)
            }

            if (filterTimeFrom || filterTimeTo) {
                filteredClassInstances = filterClassInstancesByDate(filteredClassInstances, filterTimeFrom, filterTimeTo)
            }
        } else {
            filteredClassInstances = classInstances
        }

        setFilterClassInstances(orderClassInstances(filteredClassInstances));

    }, [filterParams])

    const onSearch = (text) => {
        setSearchValue(text);

        if ((!text || text.trim().length === 0) && !filterParams) {
            setFilterClassInstances(searchClassInstances(classInstances, text))
            return
        }

        setFilterClassInstances(searchClassInstances(filterClassInstances, text))
    }

    const applyBookedFilter = (params) => {
        setFilterParams({...params, date: new Date()});
        setShowFilterDialog(false)
    }

    function syncDashboardItems() {
        setRefreshing(true)
        setSearchValue(null)
        setFilterParams(null)

        AsyncStorage.getItem(constStoreKeys.classInstancesInCart).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            setClassInstancesInCart(classInstanceArray)
        })

        getAllClasses((result) => {
            const sortedResult = orderClassInstances(result)
            setClassInstances(sortedResult);
            setFilterClassInstances(sortedResult);

            getAllBookedClasses(currentUser, result => {
                setBookedClasses(result)
                setRefreshing(false)
            })
        })
    }

    const addToCart = () => {
        setSelectedItem(null)
        AsyncStorage.getItem(constStoreKeys.classInstancesInCart).then(value => {
            const classInstanceArray = JSON.parse(value ?? "{}")
            classInstanceArray[selectedItem.docId] = selectedItem
            setClassInstancesInCart(classInstanceArray)
            AsyncStorage.setItem(constStoreKeys.classInstancesInCart, JSON.stringify(classInstanceArray)).then(() => {
                Toaster.show({type: 'success', message: "Class added to cart!"})
            }).catch(error => {
                console.error(error)
                Toaster.show({type: 'error', message: "Error adding class to cart"})
            })
        }).catch(error => {
            console.error(error)
            Toaster.show({type: 'error', message: "Error adding class to cart"})
        })
    }

    const logout = () => {
        auth.signOut().then(() => {
            AsyncStorage.removeItem(ACCOUNT_AUTHENTICATED).then(() => {
                navigation.replace("GetStarted")
            }).catch(error => {
                console.error(error)
                Toaster.show({type: 'error', message: "Error signing out. Please try again!"})
            })
        }).catch(error => {
            console.error(error)
            Toaster.show({type: 'error', message: "Error signing out. Please try again!"})
        })
    }

    return (
        <View style={DashboardScreenStyles.container}>
            <View style={DashboardScreenStyles.header}>
                <Image style={{height: 70, width: 150}} source={require("../../assets/icon.png")}/>
                <View style={{flexDirection: 'row'}}>
                    <Pressable onPress={() => navigation.navigate("CheckoutScreen")} style={{display: 'flex'}}>
                        <View style={{position: 'relative'}}>
                            {classInstancesInCart && Object.keys(classInstancesInCart).length > 0 &&
                                <Text style={DashboardScreenStyles.badge}>
                                    {Object.keys(classInstancesInCart).length}
                                </Text>}
                            <Image resizeMode={"center"}
                                   style={{height: 50, width: 24}}
                                   source={require("../../assets/icons/cart.png")}/>
                        </View>
                    </Pressable>
                    <Pressable onPress={logout}>
                        <Image resizeMode={"center"}
                               style={{height: 50, width: 50, marginHorizontal: 10}}
                               source={require("../../assets/icons/logout.png")}/>
                    </Pressable>
                </View>
            </View>

            <View style={{paddingHorizontal: constDimensions.screenPaddingHorizontal, marginTop: 15, marginBottom: 15}}>
                <EditText placeholder={constStrings.searchClassInstances} value={searchValue} onChangeText={onSearch}>
                    <Pressable onPress={() => setShowFilterDialog(true)}
                               style={{position: 'absolute', right: 0, borderRadius: 50}}>
                        <Image resizeMode={"center"} style={{height: 50, width: 50}}
                               source={require("../../assets/icons/filter.png")}/>
                    </Pressable>
                </EditText>
            </View>

            <View style={{paddingHorizontal: constDimensions.screenPaddingHorizontal}}>
                <FlatList data={filterClassInstances} renderItem={({item}) =>
                    <ClassInstanceItem onPress={() => setSelectedItem(item)} item={item}/>}
                          refreshControl={<RefreshControl refreshing={refreshing} onRefresh={syncDashboardItems}/>}
                />
            </View>

            {!refreshing && filterClassInstances.length === 0 &&
                <ScrollView refreshControl={<RefreshControl refreshing={refreshing} onRefresh={syncDashboardItems}/>}>
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

            {selectedItem &&
                <ViewClassInstanceItem item={selectedItem} ctaPress={addToCart} navigation={navigation}
                                       isInCart={isInClassesInstanceArray(classInstancesInCart, selectedItem)}
                                       isBooked={isInClassesInstanceArray(bookedClasses, selectedItem)}
                                       onToggle={() => setSelectedItem(null)}/>}

            {showFilterDialog &&
                <UseFilterView item={selectedItem} ctaPress={applyBookedFilter} filterParams={filterParams}
                               onToggle={() => setShowFilterDialog(false)}/>}
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
        backgroundColor: DYogaColors.colorPrimaryDark,
        color: DYogaColors.white,
        verticalAlign: 'middle',
        paddingHorizontal: 5,
        fontSize: 11,
        borderRadius: 50,
        textAlign: 'center',
        position: 'absolute',
        right: -5,
        top: 7,
        zIndex: 2
    }
});

