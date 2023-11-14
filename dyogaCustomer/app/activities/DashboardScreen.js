import {StatusBar} from 'expo-status-bar';
import {FlatList, Image, Pressable, StyleSheet, Text, View} from 'react-native';
import {constDimensions, constStrings} from "../utils/Constants";
import {DYogaColors} from "../utils/DYogaColors";
import EditText from "../components/EditText";
import {useEffect, useState} from "react";
import ClassInstanceItem from "../components/ClassInstanceItem";
import ViewClassInstanceItem from "../components/ViewClassInstanceItem";
import {auth} from "../firebase";
import { getAllClasses } from '../data/classRepository';

export default function DashboardScreen({navigation}) {

    const currentUser = auth.currentUser
    const [filterApplied, setFilterApplied] = useState(false)
    const [selectedItem, setSelectedItem] = useState()
    const [classInstances, setClassInstances] = useState([])

    console.log("current user", currentUser)
    const applyBookedFilter = () => {

    }

    useEffect(()=>{

        getAllClasses((result) =>{

            //console.log(result)
            setClassInstances(result);
        })
    },[])

    const addToCart = () => {

        setSelectedItem(null)
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
                <EditText placeholder={constStrings.searchClassInstances}/>
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
                <FlatList data={classInstances} renderItem={({item}) => <ClassInstanceItem onPress={() => setSelectedItem(item)} item={item} />}
                />
            </View>

            {selectedItem && <ViewClassInstanceItem item={selectedItem} ctaPress={addToCart} onToggle={() => setSelectedItem(null)} />}

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

