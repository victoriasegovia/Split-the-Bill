export interface Expense {
    expenseId: number;
    title: string;
    payerId: number;
    groupId: number;
    totalAmount: number;
}

export interface ExpenseRequest {
    expenseId: number | null;
    title: string;
    payerId: number;
    groupId: number;
    totalAmount: number;
}