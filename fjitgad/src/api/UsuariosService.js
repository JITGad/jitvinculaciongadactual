import FetchMaster from "./FetchMaster";

class AuthService {
    async login(user) {
        return new Promise(async (resolve, reject) => {
            await FetchMaster.post('users/logIn', user, (response) => {
                if (response.status.error) {
                    reject(response.status.message);
                    return;
                }

                if (!response.status.error && response.data.user_token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                resolve(response.data);
            });
        });
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService();