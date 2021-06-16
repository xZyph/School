import mongoose from 'mongoose';

const eventSchema = new mongoose.Schema({
  title: String,
  description: String,
  longDescription: String,
  fromDate: Date,
  toDate: Date,
  owner: { type: mongoose.Schema.Types.ObjectId, ref: 'user' },
  maxAttendees: Number,
  attendees: [{ type: mongoose.Schema.Types.ObjectId, ref:'user' }]
});

eventSchema.set('timestamps', true);

export default mongoose.model('event', eventSchema);