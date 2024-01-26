
import api from "./config";

const imagesEndpoint = '/images'

export async function uploadImage(image: File): Promise<string> {
    const formData = new FormData();
    formData.append('image', image);

    const response = await api.post(imagesEndpoint, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });

    return response.data;
}

export async function getImage(URL: string): Promise<Blob> {
    console.log("URL img " + URL)
    
    const response = await api.get(URL)
    return response.data as Blob;  
};