// Made by GRPTeam202205
import axios from 'axios'

const SALE_BASE_URL = 'sales'
const RETURN_SALE_BASE_URL = 'returnSales'

class SaleService{
    getSales(){
        return axios.get(SALE_BASE_URL);
    }
    getReturnSales(){
        return axios.get(RETURN_SALE_BASE_URL);
    }
    getSalesWithinGivenDates(startDate, endDate){
        return axios.get(`salesWithinGivenDates/${(startDate)}/${(endDate)}`);
    }
    getReturnSalesWithinGivenDates(startDate, endDate){
        return axios.get(`returnSalesWithinGivenDates/${(startDate)}/${(endDate)}`);
    }
    sort(pageNum, itemsPerPage, field){
        return axios.get(`salePaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    sortReturnSales(pageNum, itemsPerPage, field){
        return axios.get(`returnSalePaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`salePagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    returnPagination(pageNum, itemsPerPage){
        return axios.get(`returnSalePagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    getSaleContent(saleName){
        return axios.get(`saleByName/${saleName}`)
    }
    getRecentSales(){
        return axios.get('recentSixSales');
    }
}

export default new SaleService()

