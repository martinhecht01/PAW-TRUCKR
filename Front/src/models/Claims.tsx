
export class Claims {
    cuit: string;
    userURL: string;
    role: string;

    constructor(cuit: string, userURL: string, role: string){
        this.cuit = cuit;
        this.userURL = userURL;
        this.role = role;
    }

    static claimsFromJson(json: any): Claims {
        return new Claims(json.sub, json.userURL, json.authorization);
    }

}