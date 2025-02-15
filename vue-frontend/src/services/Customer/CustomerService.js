// Made by GRPTeam202205
import axios from 'axios'

const CUSTOMER_BASE_URL = 'customers'

class CustomerService{
    getCustomers(){
        return axios.get(CUSTOMER_BASE_URL);
    }
    sortAscending(pageNum, itemsPerPage, field){
        return axios.get(`customerPaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`customerPagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    deleteCustomer(customerId){
        return axios.delete(`deleteCustomer/${customerId}`);
    }
}

export default new CustomerService()