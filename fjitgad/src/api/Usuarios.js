import FetchMaster from "./FetchMaster";

export async function Login(email = '', password = '') { 
    return new Promise(async (resolve) => {
        await FetchMaster.post('users/logIn', {
            'email': email,
            'password': password
        }, (data) => resolve(data), (error) => resolve({}));
    });
}