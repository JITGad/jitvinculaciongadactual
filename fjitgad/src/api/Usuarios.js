import FetchMaster from "./FetchMaster";

class AuthService {
    async Login(user) {
        return new Promise(async (resolve, reject) => {
            await FetchMaster.post('users/logIn', {
                'email': user.email,
                'password': user.password
            }, (response) => {
                response = JSON.parse(response);
                if (response.flag && response.data.user_token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                resolve(response);
            }, (error) => reject(error));
        });
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();