// Made by GRPTeam202205
import axios from 'axios'

const Bilibili_BASE_URL = 'bilibili';

class BilibiliService{
    getRecords(userName){
        return axios.get(`records/${userName}`);
    }
    getAllRecords(){
        return axios.get(Bilibili_BASE_URL);
    }
    getDistinctRecords(){
        return axios.get('sortBilibiliRecordsByName');
    }
    deleteRecord(recordName){
        return axios.delete(`deleteBilibiliRecordByName/${recordName}`);
    }
    sortAscending(pageNum, itemsPerPage, field){
        return axios.get(`bilibiliPaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`bilibiliPagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    getRecordsWithGivenDates(startDate, endDate){
        return axios.get(`bilibiliWithinGivenDates/${(startDate)}/${(endDate)}`);
    }
    getRemainingQuantity(name, supplier){
        return axios.get(`/remainingShells/${(name)}/${(supplier)}`)
    }
}

export default new BilibiliService()

