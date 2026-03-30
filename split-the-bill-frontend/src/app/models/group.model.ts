export interface Group {
    groupId: number;
    name: string;
    memberNames: string[];
}

export interface GroupRequest {
    groupId: number | null;
    name: string;
}