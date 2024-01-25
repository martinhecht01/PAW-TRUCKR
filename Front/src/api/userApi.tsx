import { User } from "../models/User";
import api from "./config";

const usersEndpoint = '/users'

export async function createUser(user: User): Promise<User> {
    const response = await api.post(usersEndpoint, User.userToJson(user));
    return User.userFromJson(response.data);
}

export async function getUserById(id: number): Promise<User> {
    const response = await api.get(`${usersEndpoint}/${id}`);
    return User.userFromJson(response.data);
}

export async function loginUser(email: string, password: string): Promise<User> {
    const credentials = btoa(`${email}:${password}`)
    const response = await api.get('/trips/0', {
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    
    });
    var token = response.headers['x-jwt']
    sessionStorage.setItem("token", token);
    return User.userFromJson(response.data);
}

export function getToken(): string | null {
    return sessionStorage.getItem("token");
}