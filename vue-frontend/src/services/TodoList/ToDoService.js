// Made by GRPTeam202205
import axios from 'axios'

const TODO_BASE_URL = 'toDoItems'

class ToDoService{
    getToDoItems(){
        return axios.get(TODO_BASE_URL);
    }
    getCheckCompletion(content){
        return axios.get('checkCompletion', {params:{content: content}});
    }
    markCompleted(content){
        return axios.get('markCompleted', {params:{content: content}});
    }
    markUncompleted(content){
        return axios.get('markUncompleted', {params:{content: content}});
    }
    deleteItemByContent(content){
        return axios.delete(`deleteItemByContent/${content}`);
    }
}

export default new ToDoService()

