// Made by GRPTeam202205
import axios from 'axios'

const SUPPLIER_BASE_URL = 'suppliers'

class SupplierService{
    getSuppliers(){
        return axios.get(SUPPLIER_BASE_URL);
    }
    deleteSupplier(supplierId){
        return axios.delete(`deleteSupplier/${supplierId}`);
    }
    sortAscending(pageNum, itemsPerPage, field){
        return axios.get(`supplierPaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`supplierPagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    getSuppliersWithGivenProduct(productName){
        return axios.get(`suppliersWithGivenProduct/${productName}`)
    }
}

export default new SupplierService()