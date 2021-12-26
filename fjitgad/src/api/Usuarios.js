import FetchMaster from "./FetchMaster";

class AuthService {
    async login(user) {
        return new Promise(async (resolve, reject) => {
            await FetchMaster.post('users/logIn', user, (response) => {
                response = JSON.parse(response);

                if (!response.flag) {
                    reject(response.message);
                    return;
                }

                if (response.flag && response.data.user_token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                resolve(response.data);
            }, (error) => reject(error.message));
        });
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();