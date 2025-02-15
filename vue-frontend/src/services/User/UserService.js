// Made by GRPTeam202205
import axios from 'axios'

const USER_BASE_URL = 'users'

class UserService{
    getUsers(){
        return axios.get(USER_BASE_URL);
    }
    sort(pageNum, itemsPerPage, field){
        return axios.get(`userPaginationAndSort/${(pageNum)}/${(itemsPerPage)}/${(field)}`);
    }
    pagination(pageNum, itemsPerPage){
        return axios.get(`userPagination/${(pageNum)}/${(itemsPerPage)}`);
    }
    getPassword(userName){
        return axios.get('getPassword', {params:{userName: userName}});
    }
    getUserRole(userName){
        return axios.get('currentRole', {params:{userName: userName}})
    }
    getUserEmail(userName){
        return axios.get('currentEmail', {params:{userName: userName}})
    }
    getUserPhone(userName){
        return axios.get('getPhoneNumber', {params:{userName: userName}})
    }
}

export default new UserService()