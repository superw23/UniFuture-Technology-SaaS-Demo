// Made by GRPTeam202205
import axios from 'axios'

const PURCHASE_BASE_URL = 'purchases'
const RETURN_PURCHASE_BASE_URL = 'returnPurchases'

class PurchaseService{
    getPurchases(){
        return axios.get(PURCHASE_BASE_URL);
    }
    getReturnPurchases(){
        return axios.get(RETURN_PURCHASE_BASE_URL);
    }
    getPurchasesWithinGivenDates(startDate, endDate){
        return axios.get(`purchasesWithinGivenDates/${(startDate)}/${(endDate)}`);
    }
    getReturnPurchasesWithinGivenDates(startDate, endDate){
        return axios.get(`returnPurchasesWithinGivenDates/${(startDate)}/${(endDate)}`);
    }
    sort(pageNum, itemsPerPage, field){
        return axios.get(`purchasePaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    sortReturnPurchases(pageNum, itemsPerPage, field){
        return axios.get(`returnPurchasePaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`purchasePagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    returnPagination(pageNum, itemsPerPage){
        return axios.get(`returnPurchasePagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    getPurchaseContent(purchaseName){
        return axios.get(`purchaseByName/${purchaseName}`)
    }
    getPurchasesWithDistinctTypes(){
        return axios.get('purchasesWithDistinctTypes');
    }
}

export default new PurchaseService()

