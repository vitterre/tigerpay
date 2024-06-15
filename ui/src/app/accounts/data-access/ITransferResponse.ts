export interface ITransferResponse {
  id: number,
  receiverId: number,
  senderId: number,
  amount: number,
  ledger: string,
  category: string,
  issuedAt: Date
}