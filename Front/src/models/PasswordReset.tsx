export class PasswordReset{
    hash: number;
    user: number;
    createDate: Date;
    completed: boolean;

    constructor(hash: number, user: number, createDate: Date, completed: boolean){
        this.hash = hash;
        this.user = user;
        this.createDate = createDate;
        this.completed = completed;
    }

    static passwordResetFromJson(json: any): PasswordReset {
        return new PasswordReset(json.hash, json.user, json.createDate, json.completed);
    }

    static passwordResetToJson(passwordReset: PasswordReset): any {
        return {
            hash: passwordReset.hash,
            user: passwordReset.user,
            createDate: passwordReset.createDate,
            completed: passwordReset.completed
        }
    }
}