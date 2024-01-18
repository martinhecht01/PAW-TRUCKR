import { User } from "../models/User";
import api from "./config";

export async function createUser(user: User): Promise<User> {
    const response = await api.post(`/users`, User.userToJson(user));
    return User.userFromJson(response.data);
}

export async function getUserById(id: number): Promise<User> {
    const response = await api.get(`/users/${id}`);
    return User.userFromJson(response.data);
}

export async function loginUser(email: string, password: string): Promise<User> {
    const response = await api.post(`/users/login`, { email, password });
    sessionStorage.setItem("token", response.data.token);
    return User.userFromJson(response.data);
}

export function getToken(): string | null {
    return sessionStorage.getItem("token");
}