import { Expense } from "./expense.model";

export interface User {
    userId?: number;
    name: string;
}

export interface UserRequest {
    userId: number | null;
    name: string;
}

export interface MemberWithExpenses extends User {
    userExpenses: Expense[];
    totalSpent: number;
}