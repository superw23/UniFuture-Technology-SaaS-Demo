// Made by GRPTeam202205
import axios from 'axios'

class RevenueService{
    getRevenueOfBilibili(){
        return axios.get('bilibiliRevenue')
    }
    getTotalOfMostPopularTypeRevenue(){
        return axios.get('totalOfMostPopularTypeRevenue')
    }
    getTotalOfSecondPopularTypeRevenue(){
        return axios.get('totalOfSecondPopularTypeRevenue')
    }
    getTotalOfThirdPopularType(){
        return axios.get('totalOfThirdPopularTypeRevenue');
    }
    getTotalOfOthersRevenue(){
        return axios.get('totalOfOtherTypeRevenue')
    }
    getOneDaySaleTotal(date){
        return axios.get(`oneDaySale/${(date)}`);
    }
    getPeriodSaleTotal(startDate, endDate){
        return axios.get(`periodSaleTotal/${(startDate)}/${(endDate)}`);
    }
}

export default new RevenueService()

