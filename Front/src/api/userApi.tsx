import { Claims } from "../models/Claims";
import { User } from "../models/User";
import api from "./config";

const usersEndpoint = '/users'

export async function createUser(user: User): Promise<User> {
    const response = await api.post(usersEndpoint, User.userToJson(user), {
        headers: {
            'Content-Type': 'application/vnd.user.v1+json',
            'Accept': 'application/vnd.user.v1+json'
        }        
    });
    if(response.status !== 201) {
        throw new Error(response.statusText);
    }
    return response.data;
}

export async function resetPasswordRequest(cuit :String): Promise<void> {
    const response = await api.post(`${usersEndpoint}`, {
        cuit: cuit
    }, {
        headers: {
            'Content-Type': 'application/vnd.resetpassword.v1+json',}
    });
    if(response.status !== 202) {
        throw new Error(response.statusText);
    }
}

export async function resetPassword(cuit : string, password: String, token: String, userId : String): Promise<void> {
    const credentials = btoa(`${cuit}:${token}`)
    const response = await api.patch(`${usersEndpoint}/${userId}`, {
        password: password
    }, {
        headers: {
            'Content-Type': 'application/vnd.user.v1+json',
            'Authorization': `Basic ${credentials}`
        }
    });
    if(response.status !== 204) {
        throw new Error(response.statusText);
    }
}

export async function getUserByUrl(url: string): Promise<User> {
    const response = await api.get(url, {
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
    return User.userFromJson(response.data);
}

export async function getUserById(id: string): Promise<User> {
    const response = await api.get(`${usersEndpoint}/${id}`);
    return User.userFromJson(response.data);
}

export async function updateUser(name: string, imageId: string, id: number): Promise<User> {
    const params : any = {};
    
    if(name) 
        params.name = name;
    

    if(imageId) 
        params.imageId = imageId;
    

    const response = await api.patch(`${usersEndpoint}/${id}`, params, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
            'Content-Type': 'application/vnd.user.v1+json'
        }
    });
    return User.userFromJson(response.data);
}

export async function loginUser(email: string, password: string): Promise<string | null> {
    try {
        const credentials = btoa(`${email}:${password}`)
        const response = await api.get('/', {
            headers: {
                'Authorization': `Basic ${credentials}`
            }
        })
        console.log(response);
        const token = response.headers['x-jwt'];
        sessionStorage.setItem("token", token );
        return token;
    } catch(e){
        return null
    }

}

export function getToken(): string | null {
    return sessionStorage.getItem("token");
}

export function getClaims() : Claims | null {
    const token = getToken();

    if(token == null) {
        return null
    }

    const parts = token.split('.');
    if(parts.length !== 3) {
        throw new Error('Invalid token');
    }

    const payload = parts[1].replace(/-/g, '+').replace(/_/g, '/');
    const decodedPayload = atob(decodeURIComponent(payload));

    try {
        const parsedPayload = JSON.parse(decodedPayload);
        return Claims.claimsFromJson(parsedPayload);
    } catch (e) {
        return null;
    }
}

