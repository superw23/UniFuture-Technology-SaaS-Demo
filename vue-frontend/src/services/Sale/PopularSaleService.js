// Made by GRPTeam202205
import axios from 'axios'

class PopularSaleService{
    getMostPopularType(){
        return axios.get('mostPopularType')
    }
    getSecondPopularType(){
        return axios.get('secondPopularType');
    }
    getThirdPopularType(){
        return axios.get('thirdPopularType')
    }
    getNumOfMostPopularType(mostPopular){
        return axios.get('mostPopularTypeNum', {params:{mostPopular: mostPopular}});
    }
    getNumOfSecondPopularType(secondPopular){
        return axios.get('secondPopularTypeNum', {params:{secondPopular: secondPopular}});
    }
    getNumOfThirdPopularType(thirdPopular){
        return axios.get('thirdPopularTypeNum', {params:{thirdPopular: thirdPopular}})
    }
    getNumOfOtherTypes(mostPopular, secondPopular, thirdPopular){
        return axios.get('otherTypeNum', {params:{
            mostPopular: mostPopular,
            secondPopular: secondPopular,
            thirdPopular: thirdPopular
        }})
    }
    
    getMostPopularProduct(){
        return axios.get('mostPopularProduct')
    }
    getSecondPopularProduct(mostPopular){
        return axios.get('secondPopularProduct', {params:{mostPopular: mostPopular}});
    }
    getThirdPopularProduct(mostPopular, secondPopular){
        return axios.get('thirdPopularProduct', {params:{
            mostPopular: mostPopular,
            secondPopular: secondPopular
        }})
    }
    getFourthPopularProduct(mostPopularP, secondPopularP, thirdPopularP){
        return axios.get('fourthPopularProduct', {params:{mostPopular:mostPopularP,
            secondPopular: secondPopularP,
            thirdPopular: thirdPopularP}  
        })
    }
    getProductSaleNum(productName){
        return axios.get(`numberOfProductSale/${(productName)}`)
    }
}

export default new PopularSaleService()

